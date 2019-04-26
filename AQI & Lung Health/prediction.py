
import pandas as pd
import numpy as np
from sklearn.feature_selection import RFECV
from sklearn.cluster import AgglomerativeClustering
from sklearn.metrics import accuracy_score
from sklearn.svm import SVC
from sklearn.model_selection import train_test_split


#importing data
df = pd.read_excel('cancer_data.xlsx')

x_data = pd.DataFrame(data=df, columns=df.columns[:-1])
y_data = df["Level"]

#feature selection
'''
#recursive feature extraction with cross validation 
estimator = SVC(kernel="linear")
selector = RFECV(estimator, min_features_to_select=15, step=1)
selector = selector.fit(x_data,y_data)
r = selector.ranking_
l = selector.support_
'''

#removing non supporting features
l = ['Patient Id','Gender','Dust Allergy','Genetic Risk','Balanced Diet','Weight Loss','Clubbing of Finger Nails','Smoking','Frequent Cold']

for i in range(len(l)):
	del x_data[l[i]]


#clustering to decrease data range for easy user input
'''
for i in x_data.columns:
    clustering = AgglomerativeClustering(n_clusters=3).fit_predict(np.array(x[i]).reshape(-1,1))
    plt.scatter(x[i],clustering)
    plt.title(i)
    plt.show()
'''


x_data["Alcohol use"].replace([1,2,3,4,5,6,7,8],[1,1,1,1,2,2,3,3], inplace=True)
x_data["chronic Lung Disease"].replace([1,2,3,4,5,6,7],[1,1,1,2,2,3,3], inplace=True)
x_data["Passive Smoker"].replace([1,2,3,4,5,6,7,8],[1,1,2,2,3,3,3,3], inplace=True)
x_data["Chest Pain"].replace([1,2,3,4,5,6,7,8,9],[1,1,2,2,3,3,3,3,3], inplace=True)
x_data["Coughing of Blood"].replace([1,2,3,4,5,6,7,8,9],[1,1,2,2,3,3,3,3,3], inplace=True)
x_data["Fatigue"].replace([1,2,3,4,5,6,7,8,9],[1,1,2,2,2,2,3,3,3], inplace=True)
x_data["Obesity"].replace([1,2,3,4,5,6,7],[1,1,2,2,3,3,3], inplace=True)
x_data["Snoring"].replace([1,2,3,4,5,6,7],[1,1,2,2,3,3,3], inplace=True)
x_data["Wheezing"].replace([1,2,3,4,5,6,7,8],[1,1,2,2,2,2,3,3], inplace=True)
x_data["Dry Cough"].replace([1,2,3,4,5,6,7],[1,1,2,2,3,3,3], inplace=True)
x_data["Shortness of Breath"].replace([1,2,3,4,5,6,7,9],[1,1,1,2,2,2,2,3], inplace=True)
x_data["Swallowing Difficulty"].replace([1,2,3,4,5,6,7,8],[1,1,1,2,2,3,3,3], inplace=True)
x_data["OccuPational Hazards"].replace([1,2,3,4,5,6,7,8],[1,1,2,2,2,2,3,3], inplace=True)

x_train, x_test, y_train, y_test = train_test_split(x_data, y_data, test_size=0.10, random_state=52)

svc_clf = SVC(kernel="linear")
svc_clf.fit(x_train,y_train)
svc_predicted = svc_clf.predict(x_test)
print(accuracy_score(y_test,svc_predicted))
