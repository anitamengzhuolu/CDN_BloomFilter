import matplotlib.pyplot as plt
import sys

script, path = sys.argv

fp = []
with open(path,'r') as f:
    for line in f:
        fprob = line
        fp.append(float(fprob))
plt.plot(fp)
plt.show()
