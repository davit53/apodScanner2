# apodScanner2

🚀 NASA APOD & Google Vision AI Image Analysis (Java)
This project uses NASA's APOD API to get a picture and analyzes it using Google Vision AI for object detection. The analysis results are displayed, providing AI-powered insights into space imagery. 🌌🔍

🔧 Setup & Installation
1️⃣ Clone the Repository

    git clone https://github.com/yourusername/nasa-apod-vision-ai-java.git
  
    cd nasa-apod-vision-ai-java

2️⃣ API Keys Setup
API key included - no need to get your own

Google Vision AI
Set up Google Cloud Vision AI on your local machine:
  1) Create a Google Cloud project and enable the Vision API.
  2) Generate a service account key (JSON file).
  3) Set the environment variable for authentication:

    export GOOGLE_APPLICATION_CREDENTIALS="path/to/your/service-account-file.json"

3️⃣ Build & Run the Project

Build the project:
  
     mvn clean install
  
📜 License
This project is open-source under the MIT License.

