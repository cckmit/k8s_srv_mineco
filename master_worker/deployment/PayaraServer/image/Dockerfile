FROM  payara/server-full

USER root

RUN mkdir -p /home/diaco/Documentos/FILESERVER/

RUN apt-get update && apt-get -y install wget libreoffice-base

RUN wget https://downloadarchive.documentfoundation.org/libreoffice/old/6.1.0.1/deb/x86_64/LibreOffice_6.1.0.1_Linux_x86-64_deb.tar.gz

RUN tar -xvf LibreOffice_6.1.0.1_Linux_x86-64_deb.tar.gz

WORKDIR /opt/payara/LibreOffice_6.1.0.1_Linux_x86-64_deb/DEBS

RUN dpkg -i *.deb

RUN apt-get install -y libglu1-mesa libxinerama1

WORKDIR ${HOME_DIR}
