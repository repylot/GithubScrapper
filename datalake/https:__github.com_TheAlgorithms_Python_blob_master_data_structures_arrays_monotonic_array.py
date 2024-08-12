<body>
 # https://leetcode.com/problems/monotonic-array/ def is_monotonic(nums: list[int]) -&gt; bool: """ Check if a list is monotonic. &gt;&gt;&gt; is_monotonic([1, 2, 2, 3]) True &gt;&gt;&gt; is_monotonic([6, 5, 4, 4]) True &gt;&gt;&gt; is_monotonic([1, 3, 2]) False """ return all(nums[i] &lt;= nums[i + 1] for i in range(len(nums) - 1)) or all( nums[i] &gt;= nums[i + 1] for i in range(len(nums) - 1) ) # Test the function with your examples if __name__ == "__main__": # Test the function with your examples print(is_monotonic([1, 2, 2, 3])) # Output: True print(is_monotonic([6, 5, 4, 4])) # Output: True print(is_monotonic([1, 3, 2])) # Output: False
</body>