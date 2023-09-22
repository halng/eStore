import pprint
from typing import Generic, Any, Dict, List, Optional, Type, TypeVar, Union

from fastapi.encoders import jsonable_encoder
from pydantic import BaseModel
from sqlalchemy.orm import Session
from app.db.base_class import Base

ModelType = TypeVar("ModelType", bound=Base)
CreateSchemaType = TypeVar("CreateSchemaType", bound=BaseModel)
UpdateSchemaType = TypeVar("UpdateSchemaType", bound=BaseModel)


class CRUDBase(Generic[ModelType, CreateSchemaType, UpdateSchemaType]):
    def __init__(self, model: Type[ModelType]):
        """
        Generic CRUD class with default method to create, read, update, delete

        Args:
            model (Type[ModelType]): SQLAlchemy model class
            schema: A Pydantic model(schema) class
        """

        self.model = model

    def get_by_id(self, db: Session, id: Any):
        return db.query(self.model).filter(self.model.id == id).first()

    def get_with_paging(self, db: Session, *, offset: int = 0, limit: int = 25):
        return db.query(self.model).offset(offset).limit(limit).all()

    def create(self, db: Session, *, obj: CreateSchemaType):
        data = jsonable_encoder(obj)
        convert_data = self.model(**data)
        db.add(convert_data)
        db.commit()
        db.refresh(convert_data)
        return jsonable_encoder(convert_data)

    def update(
        self,
        db: Session,
        *,
        origin_obj: ModelType,
        new_obj: Union[UpdateSchemaType, Dict[str, any]]
    ):
        origin_data_converted = jsonable_encoder(origin_obj)

        if isinstance(new_obj, dict):
            update_data = new_obj
        else:
            update_data = new_obj.model_dump(exclude_unset=True)

        for field in origin_data_converted:
            if field in update_data:
                setattr(origin_obj, field, update_data[field])

        db.add(origin_obj)
        db.commit()
        db.refresh(origin_obj)
        return origin_obj

    def remove(self, db: Session, *, id: Any):
        obj = db.query(self.model).get(id)

        db.delete(obj)
        db.commit()
        return obj
