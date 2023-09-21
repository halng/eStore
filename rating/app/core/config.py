from pydantic import AnyHttpUrl, HttpUrl, PostgresDsn, validator
from typing import Any, Dict, List, Optional, Union
from pydantic_settings import BaseSettings


class Settings(BaseSettings):
    """
    Config for app
    """

    API_V1_URL: str = "/api/v1"
    SERVER_NAME: str = "rating app"
    SERVER_HOST: str = "localhost"
    HOST: str = "localhost"
    BACKEND_CORS_ORIGINS: List[AnyHttpUrl] = [
        "http://localhost:9090",
        "http://localhost:3000",
        "http://localhost:3000",
    ]

    @validator("BACKEND_CORS_ORIGINS", pre=True)
    def assemble_cors_origins(cls, v: Union[str, List[str]]) -> Union[List[str], str]:
        if isinstance(v, str) and not v.startswith("["):
            return [i.strip() for i in v.split(",")]
        elif isinstance(v, (list, str)):
            return v
        raise ValueError(v)

    # config for database
    # POSTGRES_SERVER: str
    # POSTGRES_USER: str
    # POSTGRES_PASSWORD: str
    # POSTGRES_DB: str
    # SQLALCHEMY_DATABASE_URI: Optional[PostgresDsn] = None

    # @validator("SQLALCHEMY_DATABASE_URI", pre=True)
    # def assemble_db_connection(cls, v: Optional[str], values: Dict[str, Any]) -> Any:
    #     if isinstance(v, str):
    #         return v
    #     return PostgresDsn.build(
    #         scheme="postgresql",
    #         username=values.get("POSTGRES_USER"),
    #         password=values.get("POSTGRES_PASSWORD"),
    #         host=values.get('POSTGRES_SERVER'),
    #         path=f"/{values.get('POSTGRES_DB') or ''}",
    #         port=values.get("PORT")
    #     )


settings = Settings()
