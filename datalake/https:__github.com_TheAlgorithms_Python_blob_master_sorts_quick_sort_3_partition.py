<body>
 def quick_sort_3partition(sorting: list, left: int, right: int) -&gt; None: if right &lt;= left: return a = i = left b = right pivot = sorting[left] while i &lt;= b: if sorting[i] &lt; pivot: sorting[a], sorting[i] = sorting[i], sorting[a] a += 1 i += 1 elif sorting[i] &gt; pivot: sorting[b], sorting[i] = sorting[i], sorting[b] b -= 1 else: i += 1 quick_sort_3partition(sorting, left, a - 1) quick_sort_3partition(sorting, b + 1, right) def quick_sort_lomuto_partition(sorting: list, left: int, right: int) -&gt; None: """ A pure Python implementation of quick sort algorithm(in-place) with Lomuto partition scheme: https://en.wikipedia.org/wiki/Quicksort#Lomuto_partition_scheme :param sorting: sort list :param left: left endpoint of sorting :param right: right endpoint of sorting :return: None Examples: &gt;&gt;&gt; nums1 = [0, 5, 3, 1, 2] &gt;&gt;&gt; quick_sort_lomuto_partition(nums1, 0, 4) &gt;&gt;&gt; nums1 [0, 1, 2, 3, 5] &gt;&gt;&gt; nums2 = [] &gt;&gt;&gt; quick_sort_lomuto_partition(nums2, 0, 0) &gt;&gt;&gt; nums2 [] &gt;&gt;&gt; nums3 = [-2, 5, 0, -4] &gt;&gt;&gt; quick_sort_lomuto_partition(nums3, 0, 3) &gt;&gt;&gt; nums3 [-4, -2, 0, 5] """ if left &lt; right: pivot_index = lomuto_partition(sorting, left, right) quick_sort_lomuto_partition(sorting, left, pivot_index - 1) quick_sort_lomuto_partition(sorting, pivot_index + 1, right) def lomuto_partition(sorting: list, left: int, right: int) -&gt; int: """ Example: &gt;&gt;&gt; lomuto_partition([1,5,7,6], 0, 3) 2 """ pivot = sorting[right] store_index = left for i in range(left, right): if sorting[i] &lt; pivot: sorting[store_index], sorting[i] = sorting[i], sorting[store_index] store_index += 1 sorting[right], sorting[store_index] = sorting[store_index], sorting[right] return store_index def three_way_radix_quicksort(sorting: list) -&gt; list: """ Three-way radix quicksort: https://en.wikipedia.org/wiki/Quicksort#Three-way_radix_quicksort First divide the list into three parts. Then recursively sort the "less than" and "greater than" partitions. &gt;&gt;&gt; three_way_radix_quicksort([]) [] &gt;&gt;&gt; three_way_radix_quicksort([1]) [1] &gt;&gt;&gt; three_way_radix_quicksort([-5, -2, 1, -2, 0, 1]) [-5, -2, -2, 0, 1, 1] &gt;&gt;&gt; three_way_radix_quicksort([1, 2, 5, 1, 2, 0, 0, 5, 2, -1]) [-1, 0, 0, 1, 1, 2, 2, 2, 5, 5] """ if len(sorting) &lt;= 1: return sorting return ( three_way_radix_quicksort([i for i in sorting if i &lt; sorting[0]]) + [i for i in sorting if i == sorting[0]] + three_way_radix_quicksort([i for i in sorting if i &gt; sorting[0]]) ) if __name__ == "__main__": import doctest doctest.testmod(verbose=True) user_input = input("Enter numbers separated by a comma:\n").strip() unsorted = [int(item) for item in user_input.split(",")] quick_sort_3partition(unsorted, 0, len(unsorted) - 1) print(unsorted)
</body>