

def test2(**test):
    print(type(test.get('m')))

if __name__ == '__main__':
    test2(m = 1, n = 2)