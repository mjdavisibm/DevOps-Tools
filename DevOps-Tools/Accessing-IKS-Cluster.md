#Accessing the DevOps-Tools IKS Cluster
It is assumed you have executed the setup instructions described in the _Setup_ folder and have  accessed a command prompt (`$`)

It is also assumed you have
1. Created a Resource Group in IBM Cloud called `DevOps-Tools`. Just in case: The `ibmcloud` command to change to the specific resource group is `ibmcloud target -g DevOps-Tools`
1. You IKS C luster is called `devops-cluster`

To log into your DevOps-Tools IKS instance to use `kubectl` etc. follow the instructions found in the 
in the _"Access"_ tab in the `devops-cluster` IKS cluster console.

The full set of commands to login to the DevOps-Tools IKS cluster are thus

	ibmcloud login -a https://cloud.ibm.com -r us-south -g DevOps-Tools
	ibmcloud ks cluster-config --cluster devops-cluster
	 ---> One of the last lines will say 
	 	export KUBECONFIG=/Users/$USER/.bluemix/plugins/container-service/clusters/devops-cluster/kube-config-dal10-devops-cluster.yml
	 	Copy and paste this line into the terminal
	export KUBECONFIG=/Users/$USER/.bluemix/plugins/container-service/clusters/devops-cluster/kube-config-dal10-devops-cluster.yml