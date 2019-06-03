import keras
import numpy as np
import os
import sys
import cv2
from keras.preprocessing.image import ImageDataGenerator
from keras.models import Sequential
from keras.layers import Dense, Dropout, Flatten
from keras.layers import Conv2D, MaxPooling2D, Activation
from keras.layers.normalization import BatchNormalization
from keras.preprocessing import image
from keras.applications.inception_v3 import preprocess_input
from keras.models import load_model


class AlexNet:

    def __init__(self):
        pass

    def train(self, **parameter):
        modelfile = './CNN_AlexNet.h5'
        train_datagen = ImageDataGenerator(rescale=1. / 255,
                                       shear_range=0.2,
                                       zoom_range=0.2,
                                       horizontal_flip=True)
        validation_datagen = ImageDataGenerator(rescale=1. / 255)
        train_generator = train_datagen.flow_from_directory(parameter.get('train_dir'),
                                                        target_size=(parameter.get('target_size'), parameter.get('target_size')),
                                                        batch_size=parameter.get('batch_size'),
                                                        class_mode='categorical')
        validation_generator = validation_datagen.flow_from_directory(parameter.get('test_dir'),
                                                                  target_size=(parameter.get('target_size'), parameter.get('target_size')),
                                                                  batch_size=parameter.get('batch_size'),
                                                                  class_mode='categorical')
        input_shape = (63, 63, 3)

        # input image dimensions
        #img_rows, img_cols = 64, 64

        model = Sequential()
        model.add(Conv2D(96, kernel_size=(5, 5), strides=(2, 2), input_shape=input_shape))
        model.add(BatchNormalization())
        model.add(Activation('relu'))
        model.add(MaxPooling2D(pool_size=(2, 2)))

        model.add(Conv2D(256, kernel_size=(5, 5), padding='same'))
        model.add(BatchNormalization())
        model.add(Activation('relu'))
        model.add(MaxPooling2D(pool_size=(2, 2)))

        model.add(Conv2D(384, kernel_size=(3, 3), padding='same'))
        model.add(BatchNormalization())
        model.add(Activation('relu'))

        model.add(Conv2D(384, kernel_size=(3, 3), padding='same'))
        model.add(BatchNormalization())
        model.add(Activation('relu'))

        model.add(Conv2D(256, kernel_size=(3, 3), padding='same'))
        model.add(BatchNormalization())
        model.add(Activation('relu'))
        model.add(MaxPooling2D(pool_size=(2, 2)))

        model.add(Flatten())
        model.add(Dense(4096))
        model.add(BatchNormalization())
        model.add(Activation('relu'))

        model.add(Dense(4096))
        model.add(BatchNormalization())
        model.add(Activation('relu'))

        model.add(Dense(parameter.get('num_classes'), activation='softmax'))

        model.compile(loss=keras.losses.categorical_crossentropy,
                      optimizer=keras.optimizers.Adadelta(),
                      metrics=['accuracy'])

        model.fit_generator(train_generator,
                            steps_per_epoch=parameter.get('steps_per_epoch'),
                            epochs=parameter.get('epochs'),
                            validation_data=validation_generator,
                            validation_steps=parameter.get('validation_steps'))
        model.save(modelfile)

    def predict(self, imagepath, classesname):
        modelfile = './CNN_AlexNet.h5'
        img = cv2.imread(imagepath)
        img = cv2.resize(img, (64, 64))
        logo_img = image.img_to_array(img)
        logo_img = np.expand_dims(logo_img, axis=0)
        logo_img = preprocess_input(logo_img)
        model = load_model(modelfile)
        model_result = model.predict_classes(logo_img)
        print(classesname[int(model_result)])
        return classesname[int(model_result)]


class VggNet:
    def __init__(self):
        pass

    def train(self, **parameter):
        modelfile = './CNN_VggNet.h5'
        input_shape = (64, 64, 3)
        train_datagen = ImageDataGenerator(rescale=1. / 255,
                                           shear_range=0.2,
                                           zoom_range=0.2,
                                           horizontal_flip=True)
        validation_datagen = ImageDataGenerator(rescale=1. / 255)
        train_generator = train_datagen.flow_from_directory(parameter.get('train_dir'),
                                                            target_size=(parameter.get('target_size'), parameter.get('target_size')),
                                                            batch_size=parameter.get('batch_size'),
                                                            class_mode='categorical')
        validation_generator = validation_datagen.flow_from_directory(parameter.get('test_dir'),
                                                                      target_size=(parameter.get('target_size'), parameter.get('target_size')),
                                                                      batch_size=parameter.get('batch_size'),
                                                                      class_mode='categorical')
        model = Sequential()

        model.add(Conv2D(64, (3, 3), padding='same', input_shape=input_shape))
        model.add(BatchNormalization())
        model.add(Activation('relu'))
        model.add(Conv2D(64, (3, 3), padding='same'))
        model.add(BatchNormalization())
        model.add(Activation('relu'))
        model.add(MaxPooling2D(pool_size=(2, 2)))

        model.add(Conv2D(128, (3, 3), padding='same'))
        model.add(BatchNormalization())
        model.add(Activation('relu'))
        model.add(Conv2D(128, (3, 3), padding='same'))
        model.add(BatchNormalization())
        model.add(Activation('relu'))
        model.add(MaxPooling2D(pool_size=(2, 2)))

        model.add(Conv2D(256, (3, 3), padding='same'))
        model.add(BatchNormalization())
        model.add(Activation('relu'))
        model.add(Conv2D(256, (3, 3), padding='same'))
        model.add(BatchNormalization())
        model.add(Activation('relu'))
        model.add(Conv2D(256, (3, 3), padding='same'))
        model.add(BatchNormalization())
        model.add(Activation('relu'))
        model.add(MaxPooling2D(pool_size=(2, 2)))

        model.add(Conv2D(512, (3, 3), padding='same'))
        model.add(BatchNormalization())
        model.add(Activation('relu'))
        model.add(Conv2D(512, (3, 3), padding='same'))
        model.add(BatchNormalization())
        model.add(Activation('relu'))
        model.add(Conv2D(512, (3, 3), padding='same'))
        model.add(BatchNormalization())
        model.add(Activation('relu'))
        model.add(MaxPooling2D(pool_size=(2, 2)))

        model.add(Flatten())
        model.add(Dense(2048))
        model.add(BatchNormalization())
        model.add(Activation('relu'))
        # model.add(Dropout(0.5))

        model.add(Dense(2048))
        model.add(BatchNormalization())
        model.add(Activation('relu'))
        # model.add(Dropout(0.5))

        model.add(Dense(parameter.get('num_classes')))
        model.add(Activation('softmax'))

        model.compile(loss=keras.losses.categorical_crossentropy,
                      optimizer=keras.optimizers.Adadelta(),
                      metrics=['accuracy'])

        model.fit_generator(train_generator,
                            steps_per_epoch=parameter.get('steps_per_epoch'),
                            epochs=parameter.get('epochs'),
                            validation_data=validation_generator,
                            validation_steps=parameter.get('validation_steps'))
        model.save(modelfile)

        return 'Complete!'

    def predict(self, imagepath, classesname):
        modelfile = './CNN_VggNet.h5'
        img = cv2.imread(imagepath)
        img = cv2.resize(img, (64, 64))
        logo_img = image.img_to_array(img)
        logo_img = np.expand_dims(logo_img, axis=0)
        logo_img = preprocess_input(logo_img)
        model = load_model(modelfile)
        model_result = model.predict_classes(logo_img)
        print(classesname[int(model_result)])

        return classesname[int(model_result)]
