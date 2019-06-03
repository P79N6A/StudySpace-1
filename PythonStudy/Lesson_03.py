li = [1, 2, 3, '123', [1, 2, 3], {1:'one', 2:'two'}]
print(type(list))
print(type(li))

#元素访问
print(li[0])
print(li[-1])
print(li[-2])

#元素查找

print(li.index('123'))
print(li.index([1, 2, 3]))
# print(li.index(-1))

#添加元素
l_a = [1, 2, 3]
l_b = [7, 8, 9]
l_c = [10, 11, 12]
l_d = {1: 'one', 2: 'two'}
l_a.append(4)
l_a.append(5)
#对比extend和append的区别,extend
l_a.extend(l_b)
l_a.append(l_c)
l_a.append(l_d)

print(l_a)

l_a = []
if not l_a:
    print("Empty")
if l_a is None:
    print("None")
print(len(l_a))