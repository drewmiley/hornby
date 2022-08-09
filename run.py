import requests

getRequest = requests.get('http://localhost:9000/platforms/Newcastle')
response = getRequest.json()
print(response['stationName'])
for service in response['services']:
    print(service)