import numpy as np
from keras.models import Sequential
from keras.layers.core import Dense, Activation
from keras.models import load_model

class BP_Neural_Network:
    def __init__(self):
        pass

    @staticmethod
    def load_y():
        f = open("./y_mean_std.txt", "r")
        y_mean = f.read();
        y_mean = y_mean.split(" ")
        f.close()
        return y_mean

    @staticmethod
    def train(**parameter):
        modelfile = './BP_model.h5'
        y_mean_std = "./y_mean_std.txt"
        data_train = np.matrix(parameter.get('data_train'))
        data_mean = np.mean(data_train, axis=0)
        data_std = np.std(data_train, axis=0)
        data_train = (data_train - data_mean) / data_std
        x_train = data_train[:, 0:(data_train.shape[1] - 1)]
        y_train = data_train[:, data_train.shape[1] - 1]
        model = Sequential()
        model.add(Dense(parameter.get('node_num'), input_dim=x_train.shape[1], kernel_initializer="uniform"))
        model.add(Activation('relu'))
        model.add(Dense(1, input_dim=parameter.get('node_num')))
        model.compile(loss='mean_squared_error', optimizer='adam')
        model.fit(x_train, y_train, epochs=parameter.get('epochs'), batch_size=parameter.get('batch_size'))
        model.save(modelfile)
        y_mean = data_mean[:, data_train.shape[1] - 1]
        y_std = data_std[:, data_train.shape[1] - 1]
        print("训练完毕")
        f = open(y_mean_std, "w")
        mean_std = str(y_mean.astype(str)) + " " + str(y_std.astype(str))
        mean_std = mean_std.replace("[", "")
        mean_std = mean_std.replace("]", "")
        mean_std = mean_std.replace("'", "")
        f.write(mean_std)

    def predict(self, data_pre):
        modelfile = './BP_model.h5'
        model = load_model(modelfile)
        mean_std = self.load_y()
        pre_result = model.predict(data_pre) * float(mean_std[1]) + float(mean_std[0])
        return pre_result

