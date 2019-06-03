'''
可变参数函数
'''
def func(name, *number):
    print(number)
    print(type(number))

func('Tom', 1, 2, 3, 4, 5, 6)

def func2(**dict):
    print(dict)
    print(type(dict))

func2(a = 1, b = 2)