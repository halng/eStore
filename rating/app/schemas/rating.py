import datetime
from typing import Optional
from pydantic import BaseModel

# Shared properties


class RatingVM(BaseModel):
    star: Optional[float] = None
    comment: Optional[str] = None
    prod_id: Optional[str] = None
    username: Optional[str] = None
    user_id: Optional[str] = None


class CreateRatingVM(RatingVM):
    pass


class UpdateRatingVm(RatingVM):
    pass


class RatingDB(RatingVM):
    id: int
    # created_timestamp: datetime


class RatingDBVM(RatingDB):
    pass


class ItemInDB(RatingDB):
    pass
