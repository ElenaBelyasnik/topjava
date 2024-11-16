#### get All Users
`curl http://localhost:8080/rest/admin/users`

#### get Users 100001
`curl http://localhost:8080/rest/admin/users/100001`

#### get All Meals
`curl http://localhost:8080/rest/profile/meals`

#### get Meals 100003
`curl http://localhost:8080/rest/profile/meals/100003`

#### filter Meals
`curl "http://localhost:8080/rest/profile/meals/filter?startDate=2020-01-30&startTime=07:00:00&endDate=2020-01-31&endTime=11:00:00"`

#### get Meals not found
`curl -v http://localhost:8080/rest/profile/meals/100008`

#### delete Meals
`curl -X DELETE http://localhost:8080/rest/profile/meals/100004`

#### create Meals
`curl -X POST -d '{"dateTime":"2020-04-01T12:00","description":"Создаваемая еда","calories":300}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/profile/meals`

#### update Meals
`curl -X PUT -d '{"dateTime":"2020-01-30T07:00", "description":"Изменяемый завтрак", "calories":200}' -H 'Content-Type: application/json' http://localhost:8080/rest/profile/meals/100003`