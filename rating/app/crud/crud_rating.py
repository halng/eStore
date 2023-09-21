from typing import List
from fastapi.encoders import jsonable_encoder
from sqlalchemy.orm import Session

from app.crud.base import CRUDBase
from app.models.rating import Rating
from app.schemas.rating import CreateRatingVM, UpdateRatingVm


class CRUDRating(CRUDBase[Rating, CreateRatingVM, UpdateRatingVm]):
    def get_by_user_id(
        self, db: Session, *, user_id: str, offset: int = 0, limit: int = 25
    ) -> List[Rating]:
        return (
            db.query(self.model)
            .filter(Rating.user_id == user_id)
            .offset(offset)
            .limit(limit)
            .all()
        )

    def get_by_product_id(
        self, db: Session, *, prod_id: str, offset: int = 0, limit: int = 25
    ) -> List[Rating]:
        return (
            db.query(self.model)
            .filter(Rating.prod_id == prod_id)
            .offset(offset)
            .limit(limit)
            .all()
        )


rating_crud = CRUDRating(Rating)
