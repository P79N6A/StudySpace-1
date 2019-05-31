import numpy as np
import Deep_Learning.BP_Neural_Nerwork as BP_Neural_Nerwork


if __name__ == '__main__':
    data_train = np.mat([[14703, 250, 66, 19199.52226, 7, 3024, 3023, 13, 6972, 7857, 0.020381, 0.00012, 325133301.2],
                         [10730, 216, 61, 8595.571496, 9, 2567, 2567, 8, 4854, 6077, 0.020908, 0.000186, 246801419.4],
                         [18045, 299, 67, 8109.965457, 10, 3475, 3474, 13, 8926, 10570, 0.019244, 0.000221,
                          471650020.4],
                         [14800, 287, 66, 18676.16881, 8, 3111, 3111, 8, 8074, 9260, 0.020117, 0.000216, 384866969.4],
                         [19048, 331, 73, 6674.933471, 9, 4003, 3999, 14, 8987, 11094, 0.018887, 0.000292, 402189511.2],
                         [18770, 297, 70, 10204.26802, 9, 3340, 3340, 13, 9009, 10746, 0.015938, 0.000229, 412389219.4],
                         [18860, 291, 68, 6391.200317, 9, 3394, 3394, 13, 8889, 10515, 0.015139, 0.000144, 706241564.2],
                         [20465, 296, 67, 4703.447989, 10, 2907, 2907, 12, 9664, 11540, 0.013304, 0.000114,
                          448913353.3],
                         [22049, 318, 74, 6291.984947, 10, 3136, 3134, 10, 10332, 12972, 0.014398, 0.000101, 859860672],
                         [21420, 289, 67, 4468.156265, 9, 3804, 3804, 12, 9571, 13134, 0.015067, 0.000038, 702937200.9],
                         [22769, 332, 71, 5671.411026, 8, 4552, 4552, 13, 11217, 14300, 0.01722, 0.000032, 891841206.9],
                         [25555, 352, 77, 14103.39354, 9, 4986, 4986, 15, 11076, 15361, 0.016333, 0.000025,
                          807333345.8],
                         [21660, 348, 72, 6798.407224, 8, 4645, 4644, 13, 11295, 13824, 0.018439, 0.000136, 1022155587],
                         [12962, 257, 59, 6968.417939, 8, 2692, 2692, 8, 7840, 8920, 0.015465, 0.000106, 785414512.3],
                         [19969, 320, 70, 9668.555094, 9, 3561, 3560, 14, 9958, 11791, 0.015304, 0.000168, 899059075.1],
                         [21046, 350, 67, 7905.089868, 9, 4548, 4546, 14, 10547, 12853, 0.016932, 0.000117,
                          882063418.2],
                         [23352, 355, 61, 6198.590871, 9, 4853, 4852, 16, 11313, 13748, 0.01391, 0.00011, 826347166.8],
                         [20001, 344, 68, 3635.853103, 9, 4012, 4012, 13, 10726, 12076, 0.014101, 0.000079,
                          892400700.1],
                         [23448, 345, 60, 6139.800395, 10, 4721, 4721, 19, 11114, 13654, 0.013637, 0.000105,
                          841292119.3],
                         [25113, 307, 65, 6252.929862, 9, 4508, 4508, 17, 11421, 14700, 0.01278, 0.000075, 928412336.9],
                         [22759, 334, 69, 9013.093411, 9, 3382, 3381, 13, 11012, 14682, 0.01101, 0.000082, 1061414667],
                         [22858, 333, 68, 8890.838623, 9, 3257, 3256, 18, 10762, 14731, 0.010491, 0.000114, 738365009]])
    data_pre = np.array([[18599, 324, 65, 13116, 9, 2829, 2829, 12, 10020, 12509, 0.013007, 0.000136]])
    data_mean = data_pre.mean()
    data_std = data_pre.std()
    data_pre = (data_pre - data_mean) / data_std
    bp_neural_network = BP_Neural_Nerwork.BP_Neural_Network()
    bp_neural_network.train(data_train=data_train, node_num=12, epochs=4000, batch_size=22)
    print(bp_neural_network.predict(data_pre))
