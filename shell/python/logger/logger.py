import logging

# The following line will basicConfig() the root handler
logging.info('DUMMY - NOT SEEN')
ll = logging.getLogger('foo')
ll.info("basic msg")
ll.setLevel('DEBUG')
ll.addHandler(logging.StreamHandler())
ll.debug('msg1')
ll.propagate = False
ll.debug('msg2')
