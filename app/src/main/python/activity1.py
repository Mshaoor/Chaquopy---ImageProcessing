#!/usr/bin/env python
# coding: utf-8

# In[1]:


import face_recognition
import argparse
import pickle
import numpy as np
import cv2
import os
import base64
#import matplotlib.pyplot as plt


# In[2]:


def face_embeddings(image,name,embedding_save_path):
    knownEncodings = []
    knownNames = []
    #image=cv2.imread(image)
    jpg_original = base64.b64decode(image)
    jpg_as_np = np.frombuffer(jpg_original, dtype=np.uint8)
    image = cv2.imdecode(jpg_as_np, flags=1)
    rgb = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)
    boxes = face_recognition.face_locations(rgb,model="cnn")
    length_boxes=len(boxes)
    #print(boxes)
    #print ()
    if length_boxes > 1:
        #print ("two faces appear in one image")
        return "More than one face appear in an image"
    else:
        encodings = face_recognition.face_encodings(rgb, boxes)
        knownEncodings.append(encodings)
        knownNames.append(name)
        data = {"encodings": knownEncodings, "names": knownNames}
        embedding_name="face_char.pickle"
        save_path=os.path.join(embedding_save_path,embedding_name)
        f = open(save_path, "wb")
        f.write(pickle.dumps(data))
        f.close()
        return "Face Registered Succesfully"


# In[4]:


# image="./test.jpeg"
# name="Qasim"
# path="./embed_face/"
# face_embeddings(image,name,path)


# In[ ]:




