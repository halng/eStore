from fastapi import APIRouter
from app.api.v1 import rating

api_router = APIRouter()
api_router.include_router(rating.router, prefix="/rating", tags=["rating"])
