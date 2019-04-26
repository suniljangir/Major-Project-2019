import pandas as pd
import numpy as np
from statsmodels.tsa.holtwinters import ExponentialSmoothing
from math import sqrt
from sklearn.metrics import mean_squared_error
from datetime import datetime
import currentaqi

df = pd.read_excel("Delhi_perfect.xlsx")
def conv(x):
    return float(x)

df["PM2_5"]=df["PM2_5"].apply(conv)
df = pd.DataFrame(data =df , columns=['Date','PM2_5'])

df = df.set_index(['Date'])


'''
plt.figure(figsize=(16,8))
plt.plot(df)

'''

previous = df[:729]
forecast = df[729:]
hat_avg = forecast.copy()



print("############################ HOLT WINTER STARTED #########################################")

final_fit = ExponentialSmoothing(np.asarray(previous["PM2_5"]), seasonal_periods=370, trend=None, seasonal='add').fit()
hat_avg['Holt_winter'] = final_fit.forecast(len(forecast))

print("############################# HOLT WINTER DONE ########################################")

'''
plt.figure(figsize=(16,8))
plt.plot(previous["PM2_5"],label='data')
plt.plot(hat_avg["Holt_winter"],label='forecast')
plt.legend(loc='best')
plt.show()

'''

today = datetime.now()
today_dateformat = str(today.year)+"-"+str(today.month)+"-"+str(today.day)
datafromtoday = hat_avg["Holt_winter"][today_dateformat:]

'''
0-50 = good
51-100 = satisfactory
101-200 = moderate
201-300 = poor
301-400 = very poor
401+ = severe
'''

def level(numericdata):
    if(numericdata <= 50):
        return "good"
    elif(numericdata >= 51 and numericdata <= 100):
        return "satisfactory"
    elif(numericdata >= 101 and numericdata <= 200):
        return "moderate"
    elif(numericdata >= 201 and numericdata <= 300):
        return "poor"
    elif(numericdata >= 301 and numericdata <= 400):
        return  "very poor"
    elif(numericdata >=401):
        return "severe"

def air_pollution(numericdata):
    if (numericdata <= 50):
        return 1
    elif (numericdata >= 51 and numericdata <= 100):
        return 2
    elif (numericdata >= 101 and numericdata <= 200):
        return 3
    elif (numericdata >= 201 and numericdata <= 300):
        return 4
    elif (numericdata >= 301 and numericdata <= 400):
        return 5
    elif (numericdata >= 401):
        return 6

air_pollution = air_pollution(int(currentaqi.currentairquality))

today_level = int(currentaqi.currentairquality)
today_level_str = level(int(currentaqi.currentairquality))
print("#####################################################################")
print("#####################################################################")
print("today predicted :"+str(datafromtoday[0]))

print("#####################################################################")

print("tommorrows air quality:"+str(datafromtoday[1]))
print("#####################################################################")

tomorrow_level = datafromtoday[1]
tomorrow_level_str = level(datafromtoday[1])

print("after that:"+str(datafromtoday[2]))
print("#####################################################################")


dayaftertomorrow_level = datafromtoday[2]
dayaftertomorrow_level_str = level(datafromtoday[2])