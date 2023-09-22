# ************************************************************* #
# Rating router support CRUD for rating and comment.
from typing import Any

from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session
from fastapi.responses import JSONResponse
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


@router.get("/user/{user_id}/{offset}")
def get_rating_by_user(
    *, db: Session = Depends(deps.get_db), user_id: str, offset: int
):
    item = rating_crud.get_by_user_id(db, user_id=user_id, offset=offset)
    return item


@router.get("/product/{prod_id}")
def get_rating_by_product(*, db: Session = Depends(deps.get_db), prod_id: str) -> Any:
    """
    get rating by product

    Args:
        prod_id (str): id of product
        db (Session, optional): _description_. Defaults to Depends(deps.get_db).

    Returns:
        obj: simple information of product about rating. Response must have: average star, amount of rater
    """
    # item = rating_crud.create(db=db, obj=data)
    pass


@router.get("/product/{prod_id}/{offset}")
def get_rating_by_product_with_paging(
    *, db: Session = Depends(deps.get_db), prod_id: str, offset: int
):
    item = rating_crud.get_by_product_id(db=db, prod_id=prod_id, offset=offset)
    return item


@router.put("/{id}")
def update_rating(
    *, db: Session = Depends(deps.get_db), data: rating.CreateRatingVM, id: int
):
    origin_obj = rating_crud.get_by_id(db=db, id=id)
    if origin_obj is not None:
        item = rating_crud.update(db=db, new_obj=data, origin_obj=origin_obj)
        return item
    else:
        raise ValueError(f"Object with id: {id} doesn't exist!")


@router.delete("/{rating_id}")
def delete_rating(*, db: Session = Depends(deps.get_db), rating_id: int):
    # TODO: check is user have permission to do this action. Only allow who created deleted
    rating_crud.remove(db=db, id=rating_id)
