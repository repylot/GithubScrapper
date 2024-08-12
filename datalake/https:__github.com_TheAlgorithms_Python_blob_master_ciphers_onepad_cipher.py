<body>
 import random class Onepad: @staticmethod def encrypt(text: str) -&gt; tuple[list[int], list[int]]: """ Function to encrypt text using pseudo-random numbers &gt;&gt;&gt; Onepad().encrypt("") ([], []) &gt;&gt;&gt; Onepad().encrypt([]) ([], []) &gt;&gt;&gt; random.seed(1) &gt;&gt;&gt; Onepad().encrypt(" ") ([6969], [69]) &gt;&gt;&gt; random.seed(1) &gt;&gt;&gt; Onepad().encrypt("Hello") ([9729, 114756, 4653, 31309, 10492], [69, 292, 33, 131, 61]) &gt;&gt;&gt; Onepad().encrypt(1) Traceback (most recent call last): ... TypeError: 'int' object is not iterable &gt;&gt;&gt; Onepad().encrypt(1.1) Traceback (most recent call last): ... TypeError: 'float' object is not iterable """ plain = [ord(i) for i in text] key = [] cipher = [] for i in plain: k = random.randint(1, 300) c = (i + k) * k cipher.append(c) key.append(k) return cipher, key @staticmethod def decrypt(cipher: list[int], key: list[int]) -&gt; str: """ Function to decrypt text using pseudo-random numbers. &gt;&gt;&gt; Onepad().decrypt([], []) '' &gt;&gt;&gt; Onepad().decrypt([35], []) '' &gt;&gt;&gt; Onepad().decrypt([], [35]) Traceback (most recent call last): ... IndexError: list index out of range &gt;&gt;&gt; random.seed(1) &gt;&gt;&gt; Onepad().decrypt([9729, 114756, 4653, 31309, 10492], [69, 292, 33, 131, 61]) 'Hello' """ plain = [] for i in range(len(key)): p = int((cipher[i] - (key[i]) ** 2) / key[i]) plain.append(chr(p)) return "".join(plain) if __name__ == "__main__": c, k = Onepad().encrypt("Hello") print(c, k) print(Onepad().decrypt(c, k))
</body>