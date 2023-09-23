from typing import Generator
from app.db.session import session_local


def get_db() -> Generator:
    try:
        db = session_local()
        yield db
    finally:
        db.close()
