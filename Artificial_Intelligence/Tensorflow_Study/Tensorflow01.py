import tensorflow as tf
import numpy as np
import sklearn
import pandas



hello = tf.constant('TensorFlow: Hello Wolrd!')
sess = tf.Session()
print(sess.run(hello))
a = tf.constant(10)
b = tf.constant(20)
print(sess.run(a + b))
