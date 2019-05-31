i = 2
while i < 10000:
    n = 2
    while n <= (i / n):
        if not (i % n):
            break
        n = n + 1
    if (n > i / n):
        print(i)
    i = i + 1