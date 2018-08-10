This project is an interpreter for the Carbon programming language, described below.

# The Carbon Programming Language
The three most distinct features of Carbon are it's data-flow execution model, architecture agnostic design and its type system. Additionally, several neat syntactic constructions are available which can simplify code.

## Data-flow Execution
Data-flow execution represents the idea that programmers have better things to do than pushing bits around and keeping state of different areas of the program in sync with eachother. Many of these concepts are proven in frameworks such as React, and their use can be expanded to become central to a language's design.

In essence, the whole program behaves like a well-crafted excel spread sheet. As data is entered at one end into input cells, intermediate and final cells update based on formulas to reflect the new values. If some input state is altered again, the outputs update with no additional intervention.

The source of many bugs is simply from some state in one part of a system being out of sync with state in another. The solution is to eliminate this state, and require that values be computed from some single source of truth. Of course implementations would want to cache values which are difficult to compute, however these caches are managed by Carbon rather than in an ad-hoc manner by the programmer which allows for a much higher degree of reliability.

You may say "This is great, but my program _does_ have state! Users click on buttons, trigger actions, and mutate state". Since Carbon aims to be the best model of real-world systems, this fundamental property cannot be ignored. Carbon has a system of events and event handlers. Events are simply triggers which can be fired, similar to callbacks. Event handlers are small imperative programs which can be composed to actually perform mutations in response to events.

```
@OpenMessage.Click -> 
    If(LoggedIn, 
        MessageDialog.Content := MessageContent;
        MessageDialog.Visible := True,
        LoginDialog.Show
    )
```

## Architecture-Agnostic Design
Archetecture-Agnostic Design means that absolutely no assumptions are made about where the code is running, other than that it is Turing complete. For example, programs in Carbon can be deployed on a CPU, GPU, microcontroller, an FPGA, or even some platform which has not been developed yet. This is accomplished by describing programs as a graph of values and relations between them. By choosing a model which is inherently more general, we gain not only the ability to deploy to radically different platforms, but also additional forms of program manipulations become easy. Let's take a look at summing a list of numbers on a CPU and an FPGA, since those represent two different approaches to computation.

Here's a Carbon program to perform the sum:
```
Input = Signal(Integer)
Sum = Input>+
```

This program takes a list called `Input`, and performs a reduction on it `+`. This behaves similarly to a fold operation in functional languages. Because  `+` is defined to include an identity element, we don't have to specify it here.

The equivalent imperative program can be written:
```
int[] input;
int sum = 0;
for (int i = 0; i < input.length; i++) {
    sum += input[i];
}
```

In this program, each number is looped over and added to our accumulator. A smart compiler may vectorize this code to obtain additional speedup. It's also likely that the CPU would pipeline these operations so a high throughput can be achieved.

In the world of FPGAs, calculations are done with logic gates rather than instructions. We don't have random access memory by default, so lets assume that every clock cycle another 32-bit number comes in on the bus, and we'll add it to the accumulator. If you aren't familiar with FPGAs, just know that the biggest difference is that with FPGAs data is always moving. Every clock cycle, calculations are performed with logic gates and stored in registers.  Here's our sum program implemented in Verilog:

```
wire[0:31] input;
reg[0:31] sum;
always (@posedge clk) 
    sum = sum + input;
end
```

Here, we declare a register to store the sum, and every clock cycle we grab whatever value is currently stored in the input and add it to the accumulator. The input is assumed to return to `0` after the sum is complete. This takes a similar approach to the imperative version, except here we've explicitly created an adder solely for the purpose of performing this accumulation operation. Let's see a "vectorization" of the above code

```
reg[0:31] sum;
reg[0:31] intsum;
always (@posedge clk) 
    intsum = input[0:31] + input[32:63];
    sum = sum + intsum;
end
```

Here, we take the current two numbers from the input bus and add them together. Then that result is added to the accumulator on the next clock cycle. Notice that all sums in this design consist of only two numbers.

The benefit of Carbon is that the program is defined in such a way that it can be interpreted in either manner. Compiling to the imperative loop is straightforward and uninteresting. Compiling to FPGA logic is accomplished by taking the stream and performing a reduction on it, storing the result in a register. It's difficult to interpret the imperative program this way directly since it's dealing with memory addresses and counters. Although there exist compilers which do perform this task, it's non-trivial and requires a significant amount of elbow grease. The FPGA program has already been decomposed into stream operations, so it's also difficult to imagine them as a loop. Since the Carbon program looks at this operation as a graph, the transformation to either execution model is greatly simplified.

This representation also allows for program-program transformations to take place as well. Lets say that you want `Sum` to store the current sum of some list, regardless of additions or deletions. The obvious implementation of this is to store the sum and add or subtract any members as they change. In an imparitive program, an implementation could get tricky quickly. Some sort of listeners would have to be added to the list, or perhaps the list would need to be manipulated through a wrapper which tracks additions and deletions. This is ugly and it's sad that even the best libraries available today can't cleanly and easily perform this operation.

Thanks to the data-flow model and the graph-like structure of the program, this operation can be implemented in the compiler transparently without the programmer ever having to know (or care) how it is accomplished.