const testData = {
    testUsers: [
        {
            "_id": "66647b7a661393618c0a0651",
            "email": "test-email1@domain.com",
            "password": "testpass2",
            "workouts": [],


        },
        {
            "_id": "66647b7a771393618c0a06a8",
            "email": "test-email2@domain.com",
            "password": "testpass2",
            "workouts": [
                    {
      "exercises": [
        
        {
          "name": "Incline DB Chest Press",
          "reps": "2",
          "sets": "2",
          "weight": "25",
          "_id": {
            "$oid": "667ec80ef6afa51122c32b3f"
          }
        },
        {
          "name": "Incline Barbell Chest Press",
          "reps": "2",
          "sets": "2",
          "weight": "25",
          "_id": {
            "$oid": "667ec80ef6afa51122c32b40"
          }
        }
      ],
      "_id": {
        "$oid": "667ec80ef6afa51122c32b3e"
      },
      "dateCreated": {
        "$date": "2024-06-28T14:26:22.981Z"
      }
                }],
            
        },

        {
            "_id": "66647b7a661377618c0a06a4",
            "email": "test-email3@domain.com",
            "password": "testpass3",
            "workouts": [],
        },
    ],
    newUser: {
        email: "test-email@domain.com",
        password: "testpass1",
    },
    invalidEmail: {
        email: null,
        password: "testpass1",
    },
    invalidPassword: {
        email: "test-email@domain.com",
        password: null,
    },
    invalidUser3: {
        email: "test-email@domain.com",
        password: "testpass1",
    },

    testWorkouts:
    {
        "exercises": [
            {
                "name": "DB Chest Press",
                "reps": "10",
                "sets": "3",
                "weight": "25"
            },
            {
                "name": "Lat Pulldown",
                "reps": "10",
                "sets": "3",
                "weight": "60"
            }

            
    ]}
}

export default testData;