# Stanford Compilers cs143
This Repo contains the Docker setup


## Steps to run
1. Clone this repo, then go inside the folder.
2. Check if you have docker installed, if not download for your machine.
3. Build the image, using `docker build -t cool .` (This does tagging of the image as well)
4. Run the container using `docker run --rm -it -v $(PWD):/app cool`. (This will do volume mounting for the current directory, so you can write your code in your host machine and run it inside docker)
5. After this step you will be in `/app` folder in docker. Then follow all the instructions given on the course website for solving the assignment problem.
