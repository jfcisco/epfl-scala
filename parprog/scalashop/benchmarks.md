# Benchmarks

## Vertical Box Blur Filter

Radius = 3, Number of Tasks = 2
sequential blur time: 1607.80557 ms
fork/join blur time: 1345.8833 ms
speedup: 1.1946099412928297

Radius = 3, Number of Tasks = 4
sequential blur time: 1817.3995899999998 ms
fork/join blur time: 481.82276 ms
speedup: 3.7719255727977643

Radius = 3, Number of Tasks = 32 (Baseline)
sequential blur time: 1575.29704 ms
fork/join blur time: 342.26645999999994 ms
speedup: 4.60254574754418 <- diminishing returns

Radius = 16, Number of Tasks = 32
sequential blur time: 7866.18113 ms
fork/join blur time: 1551.66661 ms
speedup: 5.069504672785348

## Horizontal Box Blur Filter

Radius = 3, Number of Tasks = 2
sequential blur time: 1763.1085399999997 ms
fork/join blur time: 864.54293 ms
speedup: 2.039353372538712

Radius = 3, Number of Tasks = 4
sequential blur time: 1776.25511 ms
fork/join blur time: 458.82135000000005 ms
speedup: 3.8713436286258256

Radius = 3, Number of Tasks 32 (Baseline)
sequential blur time: 1590.63326 ms
fork/join blur time: 386.04444 ms
speedup: 4.120337181905793

