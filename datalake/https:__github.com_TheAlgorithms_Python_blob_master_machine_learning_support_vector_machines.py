<body>
 import numpy as np from numpy import ndarray from scipy.optimize import Bounds, LinearConstraint, minimize def norm_squared(vector: ndarray) -&gt; float: """ Return the squared second norm of vector norm_squared(v) = sum(x * x for x in v) Args: vector (ndarray): input vector Returns: float: squared second norm of vector &gt;&gt;&gt; norm_squared([1, 2]) 5 &gt;&gt;&gt; norm_squared(np.asarray([1, 2])) 5 &gt;&gt;&gt; norm_squared([0, 0]) 0 """ return np.dot(vector, vector) class SVC: """ Support Vector Classifier Args: kernel (str): kernel to use. Default: linear Possible choices: - linear regularization: constraint for soft margin (data not linearly separable) Default: unbound &gt;&gt;&gt; SVC(kernel="asdf") Traceback (most recent call last): ... ValueError: Unknown kernel: asdf &gt;&gt;&gt; SVC(kernel="rbf") Traceback (most recent call last): ... ValueError: rbf kernel requires gamma &gt;&gt;&gt; SVC(kernel="rbf", gamma=-1) Traceback (most recent call last): ... ValueError: gamma must be &gt; 0 """ def __init__( self, *, regularization: float = np.inf, kernel: str = "linear", gamma: float = 0.0, ) -&gt; None: self.regularization = regularization self.gamma = gamma if kernel == "linear": self.kernel = self.__linear elif kernel == "rbf": if self.gamma == 0: raise ValueError("rbf kernel requires gamma") if not isinstance(self.gamma, (float, int)): raise ValueError("gamma must be float or int") if not self.gamma &gt; 0: raise ValueError("gamma must be &gt; 0") self.kernel = self.__rbf # in the future, there could be a default value like in sklearn # sklear: def_gamma = 1/(n_features * X.var()) (wiki) # previously it was 1/(n_features) else: msg = f"Unknown kernel: {kernel}" raise ValueError(msg) # kernels def __linear(self, vector1: ndarray, vector2: ndarray) -&gt; float: """Linear kernel (as if no kernel used at all)""" return np.dot(vector1, vector2) def __rbf(self, vector1: ndarray, vector2: ndarray) -&gt; float: """ RBF: Radial Basis Function Kernel Note: for more information see: https://en.wikipedia.org/wiki/Radial_basis_function_kernel Args: vector1 (ndarray): first vector vector2 (ndarray): second vector) Returns: float: exp(-(gamma * norm_squared(vector1 - vector2))) """ return np.exp(-(self.gamma * norm_squared(vector1 - vector2))) def fit(self, observations: list[ndarray], classes: ndarray) -&gt; None: """ Fits the SVC with a set of observations. Args: observations (list[ndarray]): list of observations classes (ndarray): classification of each observation (in {1, -1}) """ self.observations = observations self.classes = classes # using Wolfe's Dual to calculate w. # Primal problem: minimize 1/2*norm_squared(w) # constraint: yn(w . xn + b) &gt;= 1 # # With l a vector # Dual problem: maximize sum_n(ln) - # 1/2 * sum_n(sum_m(ln*lm*yn*ym*xn . xm)) # constraint: self.C &gt;= ln &gt;= 0 # and sum_n(ln*yn) = 0 # Then we get w using w = sum_n(ln*yn*xn) # At the end we can get b ~= mean(yn - w . xn) # # Since we use kernels, we only need l_star to calculate b # and to classify observations (n,) = np.shape(classes) def to_minimize(candidate: ndarray) -&gt; float: """ Opposite of the function to maximize Args: candidate (ndarray): candidate array to test Return: float: Wolfe's Dual result to minimize """ s = 0 (n,) = np.shape(candidate) for i in range(n): for j in range(n): s += ( candidate[i] * candidate[j] * classes[i] * classes[j] * self.kernel(observations[i], observations[j]) ) return 1 / 2 * s - sum(candidate) ly_contraint = LinearConstraint(classes, 0, 0) l_bounds = Bounds(0, self.regularization) l_star = minimize( to_minimize, np.ones(n), bounds=l_bounds, constraints=[ly_contraint] ).x self.optimum = l_star # calculating mean offset of separation plane to points s = 0 for i in range(n): for j in range(n): s += classes[i] - classes[i] * self.optimum[i] * self.kernel( observations[i], observations[j] ) self.offset = s / n def predict(self, observation: ndarray) -&gt; int: """ Get the expected class of an observation Args: observation (Vector): observation Returns: int {1, -1}: expected class &gt;&gt;&gt; xs = [ ... np.asarray([0, 1]), np.asarray([0, 2]), ... np.asarray([1, 1]), np.asarray([1, 2]) ... ] &gt;&gt;&gt; y = np.asarray([1, 1, -1, -1]) &gt;&gt;&gt; s = SVC() &gt;&gt;&gt; s.fit(xs, y) &gt;&gt;&gt; s.predict(np.asarray([0, 1])) 1 &gt;&gt;&gt; s.predict(np.asarray([1, 1])) -1 &gt;&gt;&gt; s.predict(np.asarray([2, 2])) -1 """ s = sum( self.optimum[n] * self.classes[n] * self.kernel(self.observations[n], observation) for n in range(len(self.classes)) ) return 1 if s + self.offset &gt;= 0 else -1 if __name__ == "__main__": import doctest doctest.testmod()
</body>