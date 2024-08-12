<body>
 from __future__ import annotations import collections import pprint from pathlib import Path def signature(word: str) -&gt; str: """Return a word sorted &gt;&gt;&gt; signature("test") 'estt' &gt;&gt;&gt; signature("this is a test") ' aehiisssttt' &gt;&gt;&gt; signature("finaltest") 'aefilnstt' """ return "".join(sorted(word)) def anagram(my_word: str) -&gt; list[str]: """Return every anagram of the given word &gt;&gt;&gt; anagram('test') ['sett', 'stet', 'test'] &gt;&gt;&gt; anagram('this is a test') [] &gt;&gt;&gt; anagram('final') ['final'] """ return word_by_signature[signature(my_word)] data: str = Path(__file__).parent.joinpath("words.txt").read_text(encoding="utf-8") word_list = sorted({word.strip().lower() for word in data.splitlines()}) word_by_signature = collections.defaultdict(list) for word in word_list: word_by_signature[signature(word)].append(word) if __name__ == "__main__": all_anagrams = {word: anagram(word) for word in word_list if len(anagram(word)) &gt; 1} with open("anagrams.txt", "w") as file: file.write("all_anagrams = \n ") file.write(pprint.pformat(all_anagrams))
</body>