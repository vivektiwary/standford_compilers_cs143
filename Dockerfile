FROM i386/ubuntu:16.04

RUN apt-get update \
    && apt-get upgrade -y

RUN apt-get install -y flex bison build-essential csh openjdk-8-jdk vim \
    libxaw7-dev wget less

RUN rm -rf /var/lib/apt/lists/*

# RUN mkdir /usr/class
WORKDIR /usr/class

COPY class_files .
# COPY student-dist.tar.gz .
# RUN wget https://s3-us-west-1.amazonaws.com/prod-edx/Compilers/Misc/student-dist.tar.gz \
#     && tar -xf student-dist.tar.gz \
#     && rm student-dist.tar.gz

RUN echo "alias pa2='cd /usr/class/cs143/cool/assignments/PA2J/'" >> ~/.bashrc 
RUN echo "alias pa3='cd /usr/class/cs143/cool/assignments/PA3J/'" >> ~/.bashrc 
RUN echo "alias pa4='cd /usr/class/cs143/cool/assignments/PA4J/'" >> ~/.bashrc 
RUN echo "alias pa5='cd /usr/class/cs143/cool/assignments/PA5J/'" >> ~/.bashrc 

RUN echo "alias make2='make -f /usr/class/cs143/cool/assignments/PA2J/Makefile'" >> ~/.bashrc 
RUN echo "alias make3='make -f /usr/class/cs143/cool/assignments/PA3J/Makefile'" >> ~/.bashrc 
RUN echo "alias make4='make -f /usr/class/cs143/cool/assignments/PA4J/Makefile'" >> ~/.bashrc 
RUN echo "alias make5='make -f /usr/class/cs143/cool/assignments/PA5J/Makefile'" >> ~/.bashrc 

ENV PATH=/usr/class/cs143/cool/bin:$PATH

# RUN mkdir /workspace
WORKDIR /app

# Don't use symbolic links
# RUN sed -i -e 's/ln -s/cp -r -L/g' /usr/class/cs143/cool/cs143/cool/cs143/assignments/*/Makefile