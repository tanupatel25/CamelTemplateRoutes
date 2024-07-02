from fastapi import FastAPI, File, UploadFile
from fastapi.responses import JSONResponse

app = FastAPI()

@app.post("/uploadfile/")
async def upload_file(file: UploadFile = File(...)):
    try:
        file_content = await file.read()
        with open(f"/Users/tanupatel/Desktop/uploaded_files/{file.filename}", "wb") as f:
            f.write(file_content)
        return JSONResponse(content={"filename": file.filename})
    except Exception as e:
        return JSONResponse(content={"error": str(e)}, status_code=500)

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8000)