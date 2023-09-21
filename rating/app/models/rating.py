from typing import TYPE_CHECKING

from sqlalchemy import Column, Integer, String, Float, DateTime
from app.db.base_class import Base
from datetime import datetime


class Rating(Base):
    id = Column(Integer, primary_key=True, index=True, autoincrement=True)
    comment = Column(String)
    star = Column(Float, index=True)
    prod_id = Column(String, index=True)
    username = Column(String)
    user_id = Column(String, index=True)
    created_timestamp = Column(DateTime, default=datetime.utcnow())
