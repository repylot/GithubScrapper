<body>
 """ In the Combination Sum problem, we are given a list consisting of distinct integers. We need to find all the combinations whose sum equals to target given. We can use an element more than one. Time complexity(Average Case): O(n!) Constraints: 1 &lt;= candidates.length &lt;= 30 2 &lt;= candidates[i] &lt;= 40 All elements of candidates are distinct. 1 &lt;= target &lt;= 40 """ def backtrack( candidates: list, path: list, answer: list, target: int, previous_index: int ) -&gt; None: """ A recursive function that searches for possible combinations. Backtracks in case of a bigger current combination value than the target value. Parameters ---------- previous_index: Last index from the previous search target: The value we need to obtain by summing our integers in the path list. answer: A list of possible combinations path: Current combination candidates: A list of integers we can use. """ if target == 0: answer.append(path.copy()) else: for index in range(previous_index, len(candidates)): if target &gt;= candidates[index]: path.append(candidates[index]) backtrack(candidates, path, answer, target - candidates[index], index) path.pop(len(path) - 1) def combination_sum(candidates: list, target: int) -&gt; list: """ &gt;&gt;&gt; combination_sum([2, 3, 5], 8) [[2, 2, 2, 2], [2, 3, 3], [3, 5]] &gt;&gt;&gt; combination_sum([2, 3, 6, 7], 7) [[2, 2, 3], [7]] &gt;&gt;&gt; combination_sum([-8, 2.3, 0], 1) Traceback (most recent call last): ... RecursionError: maximum recursion depth exceeded """ path = [] # type: list[int] answer = [] # type: list[int] backtrack(candidates, path, answer, target, 0) return answer def main() -&gt; None: print(combination_sum([-8, 2.3, 0], 1)) if __name__ == "__main__": import doctest doctest.testmod() main()
</body>