import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
from sklearn.preprocessing import PolynomialFeatures
from sklearn.linear_model import LinearRegression, Perceptron
from sklearn.metrics import mean_squared_error, r2_score
from sklearn.model_selection import train_test_split


def FileLoad():
    ssq = pd.read_csv("./ssq.txt")
    return np.matrix(ssq.get_values())


def X_fitting(month, x_train, xi):
    x_train = np.matrix(x_train)
    X = np.arange(x_train.shape[0])
    X = np.array(X).reshape(-1, 1)
    y = x_train[:, xi]
    x_train, x_test, y_train, y_test = train_test_split(X, y, test_size=0.1)
    rmses = []
    pre_month = np.array(x_train.shape[0] + month).reshape(-1, 1)
    degrees = np.arange(1, 50)
    min_rmse, min_deg, score = 1e10, 0, 0

    for deg in degrees:
        # 生成多项式特征集(如根据degree=3 ,生成 [[x,x**2,x**3]] )
        poly = PolynomialFeatures(degree=deg, include_bias=False)
        x_train_poly = poly.fit_transform(x_train)
        print(x_train_poly)
        # 多项式拟合
        poly_reg = LinearRegression()

        poly_reg.fit(x_train_poly, y_train)
        #        poly_reg.fit(x_train_poly, y)
        #        print(poly_reg.coef_,poly_reg.intercept_) #系数及常数

        # 测试集比较
        x_test_poly = poly.fit_transform(x_test)
        #        x_test_poly = poly.fit_transform(X)
        y_test_pred = poly_reg.predict(x_test_poly)
        #        y_test_pred = poly_reg.predict(x_test_poly)
        x_pred_cache = poly.fit_transform(pre_month)
        y_pred_cache = poly_reg.predict(x_pred_cache)
        #        print(y_pred)
        # mean_squared_error(y_true, y_pred) #均方误差回归损失,越小越好。
        poly_rmse = np.sqrt(mean_squared_error(y_test, y_test_pred))
        rmses.append(poly_rmse)
        # r2 范围[0，1]，R2越接近1拟合越好。
        r2score = r2_score(y_test, y_test_pred)

        # degree交叉验证
        if min_rmse > poly_rmse:
            min_rmse = poly_rmse
            min_deg = deg
            score = r2score
            y_pred = y_pred_cache
        print('degree = %s, RMSE = %.2f ,r2_score = %.2f' % (deg, poly_rmse, r2score))
#    print(min_rmse)
    return y_pred, min_rmse

if __name__ == '__main__':
    ssq = FileLoad()
    print(ssq)
    # y_pred, rmse = X_fitting(1, ssq, 0)
    # while(rmse > 0.1):
    #    y_pred, rmse = X_fitting(1, ssq, 0)
    #    print(y_pred)



