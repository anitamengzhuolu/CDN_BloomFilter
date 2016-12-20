import math
import matplotlib.pyplot as plt

m = 80000
n = 0.1 * m
p = []

for k in range(1,100):
    fp = math.pow(1-math.exp(-k*n/m),k)
    p.append(fp)
plt.plot(p)
plt.show()