from core.models import Schema
from proto.edu.uci.ics.amber.core import (
    ChannelIdentity,
    ActorVirtualIdentity,
    PortIdentity,
)
from proto.edu.uci.ics.amber.engine.architecture.rpc import (
    WorkerStateResponse,
    EmptyRequest,
)
from proto.edu.uci.ics.amber.engine.architecture.worker import (
    WorkerState,
)
from core.architecture.handlers.control.control_handler_base import ControlHandler
from core.architecture.packaging.input_manager import InputManager
from core.models.internal_queue import DataElement

from loguru import logger


class StartWorkerHandler(ControlHandler):

    async def start_worker(self, req: EmptyRequest) -> WorkerStateResponse:
        logger.info("Starting the worker.")
        if self.context.executor_manager.executor.is_source:
            self.context.state_manager.transit_to(WorkerState.RUNNING)
            input_channel_id = ChannelIdentity(
                InputManager.SOURCE_STARTER,
                ActorVirtualIdentity(self.context.worker_id),
                False,
            )
            port_id = PortIdentity(0, False)
            self.context.input_manager.add_input_port(port_id, Schema())
            self.context.input_manager.register_input(input_channel_id, port_id)
            self.context.input_queue.put(
                DataElement(
                    tag=input_channel_id,
                    payload=None,
                )
            )
        state = self.context.state_manager.get_current_state()
        return WorkerStateResponse(state)
