import time
from bs4 import BeautifulSoup
from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.chrome.options import Options

#Configuring selenium options
options = Options()
options.headless = True

#Configuring selenium webdriver
driver = webdriver.Chrome('/home/nishant/Desktop/major project/chromedriver')

#Getting webpage
driver.get('https://app.cpcbccr.com/AQI_India/')

time.sleep(5)


#Getting to the station on the webpage
id = driver.find_element_by_xpath('//*[@id="stations"]/option[4]')
id.click()
del id

time.sleep(5)

#Getting outer HTML
elem = driver.find_element_by_xpath('//*')
page = elem.get_attribute('outerHTML')

#Destroying selenium webdriver instance
driver.close()

#Creating soup and finding the avg
soup = BeautifulSoup(page, 'html.parser')
lister = soup.findAll('td')
for i in lister:
	check = i.get('class')
	if str(check).count('avg-value')>0:
		avg = i.text
		break

#Result
currentairquality = avg

print("############### SELENIUM DONE CURRENT AQI FETCHED ######################################################")

print("current aqi :"+str(currentairquality))