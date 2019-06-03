import Deep_Learning.CNN as cnn

if __name__ == '__main__':
#    cnn.VggNet().train(train_dir="./images/train/", test_dir="./images/val", target_size=64,
#                      num_classes=7, steps_per_epoch=256, batch_size=32, epochs=5, validation_steps=156)
    cnn.VggNet().predict()

