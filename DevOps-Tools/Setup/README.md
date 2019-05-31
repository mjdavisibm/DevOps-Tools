This section describes how to setup your environment to be able to communicate with the IKS instance an perform all the necessary Command Line Interface (CLI) commands. 

There are three ways to setup.
1. **Laptop**. Using your laptop. This requires installing all the CLIs, plug-ins and Git repo locally on your laptop.
2. **Docker**. Using a Docker `ubuntu` image on your laptop using the [Docker app](https://www.docker.com/get-started). This requires the `docker` CLI to be installed on your laptop.
3. **IKS**. Using the same Docker `ubuntu` image but executing in you DevOps-Tools IKS instance and accessing it using `ibmcloud` CLI. This requires installing the `ibmcloud` CLI on your laptop

The advantages of the latter two are 
1. No peculiar setup on your laptop will cause installation problems of the CLIs, plug-ins or git repositories. 
2. The entire DevOps-Tools commands do not muck up your own laptop.

The main difference with the 3 options are how you first access the command prompt to execute the setup commands described below
1. **Laptop**. You start a command prompt or terminal. 
2. **Docker**. You start a command prompt and access the `ubuntu` Docker container using `docker` commands. See the DevOps-Tools-Image folder for information
3. **IKS**. You start a command prompt and log on to the IKS cluster first and then access the `ubuntu` image. See the DevOps-Tools-Image folder for information

Once you have setup you environment the following will have been installed - Some of these might have already been installed in the laptop option.
1. [helm](https://helm.sh/docs/)
1. [kubectl](https://kubernetes.io/docs/reference/kubectl/overview/)
1. [ibmcloud](https://cloud.ibm.com/docs/cli?topic=cloud-cli-ibmcloud_cli)
1. [git](https://git-scm.com/docs)
1. [docker](https://docs.docker.com/engine/reference)
1. [curl](https://ec.haxx.se/cmdline-options.html)
1. Homebrew (Mac only)
1. [chartmuseum](https://chartmuseum.com/docs/#configuration)

In addition, the following plug-ins will have been installed
1. IBM Cloud Developer Tools plug-in
2. IBM Cloud Functions plug-in
3. IBM Cloud Container Registry plug-in
4. IBM Cloud Kubernetes Service plug-in
5. sdk-gen plug-in

#Setting up your environment
Once at the command prompt for the option you have chosen follow the following commands to setup the environment

#####1. Install curl - This is only required for the `ubuntu` images for options 2 and 3 above
	sudo apt update
	sudo apt-get -y install curl
#####2. Install IBM cloud tools
More information on the [CLIs installed](https://cloud.ibm.com/docs/cli?topic=cloud-cli-ibmcloud-cli)

	curl -sL https://ibm.biz/idt-installer | bash
#####3. Initialize and add Helm repositories
	helm init --client-only
	helm repo add ibm-charts https://icr.io/helm/ibm-charts
	helm repo add iks-charts https://icr.io/helm/iks-charts
	helm repo add ibm-community https://icr.io/helm/ibm-community
	helm repo update
#####4. Install Chart Museum
See [GitHub](https://github.com/helm/chartmuseum) for more details on installing ChartMuseum
* On MacOS

	curl -LO https://s3.amazonaws.com/chartmuseum/release/latest/bin/darwin/amd64/chartmuseum
	chmod +x ./chartmuseum
	sudo mv ./chartmuseum /usr/local/bin
	chartmuseum --version
* On `ubuntu` image

	curl -LO https://s3.amazonaws.com/chartmuseum/release/latest/bin/linux/amd64/chartmuseum
	chmod +x ./chartmuseum
	mv ./chartmuseum /usr/local/bin
	chartmuseum --version
#####5. Clone this repository into the image - we will be using many of the scripts on the command line
	git clone https://github.com/mjdavisibm/devops-tools