"""init table

Revision ID: 3246c6d8d961
Revises: 
Create Date: 2023-09-22 19:05:06.243503

"""
from typing import Sequence, Union

from alembic import op
import sqlalchemy as sa
from datetime import datetime

# revision identifiers, used by Alembic.
revision: str = "3246c6d8d961"
down_revision: Union[str, None] = None
branch_labels: Union[str, Sequence[str], None] = None
depends_on: Union[str, Sequence[str], None] = None


def upgrade() -> None:
    op.create_table(
        "rating",
        sa.Column("id", sa.Integer(), nullable=False),
        sa.Column("comment", sa.String(256), nullable=True),
        sa.Column("star", sa.Float(), nullable=True),
        sa.Column("prod_id", sa.String(), nullable=False),
        sa.Column("username", sa.String(), nullable=False),
        sa.Column("user_id", sa.String(), nullable=False),
        sa.Column(
            "created_timestamp",
            sa.DateTime(),
            default=datetime.utcnow(),
            nullable=False,
        ),
        sa.PrimaryKeyConstraint("id"),
    )
    op.create_index(op.f("ix_prod_id"), "rating", ["prod_id"], unique=False)
    op.create_index(op.f("ix_user_id"), "rating", ["user_id"], unique=False)


def downgrade() -> None:
    op.drop_index(op.f("ix_prod_id"), "rating")
    op.drop_index(op.f("ix_user_id"), "rating")
    op.drop_table("rating")
