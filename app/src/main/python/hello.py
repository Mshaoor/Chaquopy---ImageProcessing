import numpy as np
import os
import cv2
import sys
import base64
# noinspection PyInterpreter
import pickle
import dlib
import face_recognition

def test(img):
    jpg_original = base64.b64decode(img)
    jpg_as_np = np.frombuffer(jpg_original, dtype=np.uint8)
    img = cv2.imdecode(jpg_as_np, flags=1)
    #pt="/storage/emulated/0/ImageProcessing"
    #path = os.path.join(pt,"test_image.png")
    #cv2.imwrite(path,img)
    pt="/storage/emulated/0/ImageProcessing"
    path = os.path.join(pt,"data.pkl")
    file = open(path, 'wb')
    pickle.dump(img, file)
    file.close()
    H,W=img.shape[:2]
    return H,W
    #return img

#test()
