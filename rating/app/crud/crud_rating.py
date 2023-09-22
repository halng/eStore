from typing import List
from fastapi.encoders import jsonable_encoder
from sqlalchemy.orm import Session

from app.crud.base import CRUDBase
from app.models.rating import Rating
from app.schemas.rating import CreateRatingVM, UpdateRatingVm


class CRUDRating(CRUDBase[Rating, CreateRatingVM, UpdateRatingVm]):
    """
    FIXME: need to add more info in response body (total_page, total_ele, current_page).
    TODO: same thing with get all rating for product

    """

    def get_by_user_id(
        self, db: Session, *, user_id: str, offset: int = 0, limit: int = 25
    ) -> List[Rating]:
        """
        Get all rating by user id

        Args:
            db (Session): database
            user_id (str): user id
            offset (int, optional): offset of data. Defaults to 0.
            limit (int, optional): limit per request. Defaults to 25.

        Returns:
            List[Rating]: {limit} rating
        """
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
