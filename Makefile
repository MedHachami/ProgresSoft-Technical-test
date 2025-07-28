.PHONY: up down test clean

up:
	cd technicaltest && docker compose up -d

down:
	cd technicaltest && docker compose down

test:
	cd technicaltest && mvn test

clean:
	cd technicaltest && mvn clean && rmdir /S /Q target
