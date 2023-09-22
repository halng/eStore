from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker

from app.core.config import settings


SQLALCHEMY_DATABASE_URL = "postgresql://postgres:" "@localhost/rating"
engine = create_engine(SQLALCHEMY_DATABASE_URL, pool_pre_ping=True)
session_local = sessionmaker(autocommit=False, autoflush=False, bind=engine)
