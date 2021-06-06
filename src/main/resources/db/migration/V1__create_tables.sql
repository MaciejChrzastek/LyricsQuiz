CREATE TABLE IF NOT EXISTS lyrics_quiz_db.song (
  `id` int NOT NULL,
  `title` varchar(45) DEFAULT NULL,
  `author` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS lyrics_quiz_db.user (
  `id` int NOT NULL,
  `email` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS lyrics_quiz_db.score (
  `id` int NOT NULL,
  `user_id` int DEFAULT NULL,
  `song_id` int DEFAULT NULL,
  `score` int DEFAULT NULL,
  `difficulty` int DEFAULT NULL,
  `max_score` int DEFAULT NULL,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id_FK_idx` (`user_id`),
  KEY `song_id_FK_idx` (`song_id`),
  CONSTRAINT `song_id_FK` FOREIGN KEY (`song_id`) REFERENCES `song` (`id`),
  CONSTRAINT `user_id_FK` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


