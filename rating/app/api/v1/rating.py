# ************************************************************* #
# Rating router support CRUD for rating and comment.
from typing import Any

from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session

from app.api import dependency as deps
from app import crud, models
from app.crud.crud_rating import rating_crud
from app.schemas import rating

router = APIRouter()


@router.post("/")
def create_rating(
    *, db: Session = Depends(deps.get_db), data: rating.CreateRatingVM
) -> Any:
    item = rating_crud.create(db=db, obj=data)
    return item
