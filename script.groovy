
def buildApp() {
    echo "Building app - triggered by ${params.NAME}"  // params and env are available in the script by default
}

def testApp() {
    echo "Testing app"
}

def deployApp() {
    echo "Deploying app"
}

return this