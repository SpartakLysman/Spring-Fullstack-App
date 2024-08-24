import {
    Container,
    Flex,
    Box,
    Heading,
    Text,
    IconButton,
    Button,
    VStack,
    HStack,
    Wrap,
    WrapItem,
    FormControl,
    FormLabel,
    Input,
    InputGroup,
    InputLeftElement,
    Textarea, Link,
} from '@chakra-ui/react'
import {
    MdPhone,
    MdEmail,
    MdLocationOn,
    MdOutlineEmail,
} from 'react-icons/md'
import { BsGithub, BsDiscord, BsPerson, BsLinkedin } from 'react-icons/bs'
import SidebarWithHeader from "../shared/SideBar.jsx";

const Contact = () => {

    return (
        <SidebarWithHeader>

            <Container bg="mediumturquoise"
                       maxW="full"
                       mt={30} centerContent
                       overflow="hidden"
                       w={{ base: 'full', md: 'full' }}
                       h="1085" >
                <Flex>
                    <Box
                        bg="#02054B"
                        color="white"
                        borderRadius="lg"
                        m={{ sm: 4, md: 16, lg: 10 }}
                        p={{ sm: 5, md: 5, lg: 16 }}>
                        <Box p={2} h={550}>
                            <Wrap spacing={{ base: 5, sm: 3, md: 5, lg: 25 }}>
                                <WrapItem>
                                    <Box>
                                        <Heading paddingTop={35} paddingLeft={70}  m={1}>Contact</Heading>
                                        <Box py={{ base: 5, sm: 5, md: 8, lg: 50 }}>
                                            <VStack pl={0} spacing={3} alignItems="flex-start">
                                                <Button
                                                    size="md"
                                                    height="48px"
                                                    width="270px"
                                                    variant="ghost"
                                                    color="#DCE2FF"
                                                    _hover={{ border: '2px solid #1C6FEB' }}
                                                    leftIcon={<MdPhone color="#1970F1" size="20px" />}>
                                                    +48571518679
                                                </Button>
                                                <Button
                                                    size="md"
                                                    height="48px"
                                                    width="270px"
                                                    variant="ghost"
                                                    color="#DCE2FF"
                                                    _hover={{ border: '2px solid #1C6FEB' }}
                                                    leftIcon={<MdEmail color="#1970F1" size="20px" />}>
                                                    spartaklysman@gmail.com
                                                </Button>
                                                <Button
                                                    size="md"
                                                    height="48px"
                                                    width="270px"
                                                    variant="ghost"
                                                    color="#DCE2FF"
                                                    _hover={{ border: '2px solid #1C6FEB' }}
                                                    leftIcon={<MdLocationOn color="#1970F1" size="20px" />}>
                                                    Warsaw, Poland
                                                </Button>
                                            </VStack>
                                        </Box>
                                        <HStack
                                            mt={{ lg: 10, md: 10 }}
                                            spacing={5}
                                            px={5}
                                            alignItems="flex-start">
                                            <Link href="https://www.linkedin.com/in/spartak-lysman/" isExternal>
                                                <IconButton
                                                    aria-label="linkedin"
                                                    variant=""
                                                    size="lg"
                                                    isRound={true}
                                                    _hover={{ bg: '#0D74FF' }}
                                                    icon={<BsLinkedin size="28px" />}
                                                />
                                            </Link>
                                            <Link href="https://github.com/SpartakLysman" isExternal>
                                                <IconButton
                                                    aria-label="github"
                                                    variant=""
                                                    size="lg"
                                                    isRound={true}
                                                    _hover={{ bg: '#0D74FF' }}
                                                    icon={<BsGithub size="28px" />}
                                                />
                                            </Link>
                                            <IconButton
                                                aria-label="discord"
                                                variant=""
                                                size="lg"
                                                isRound={true}
                                                _hover={{ bg: '#0D74FF' }}
                                                icon={<BsDiscord size="28px" />}
                                            />
                                        </HStack>
                                    </Box>
                                </WrapItem>
                                <WrapItem>
                                    <Box
                                        position="absolute"
                                        top="82.5%"
                                        left="82.5%"
                                        transform="translate(-195%, -365%)"
                                        display="flex"
                                        alignItems="center"
                                        justifyContent="center"
                                        fontSize="2xl"
                                    >
                                    <Text mt={{ sm: 3, md: 3, lg: 5 }}
                                          color="gray.100"
                                          marginY={'40'}>
                                        Fill up the form below to contact
                                    </Text>
                                    </Box>
                                    <Box bg="white" borderRadius="lg" m={42}>

                                        <Box m={35} h={400} w={550} color="#0B0E3F">
                                            <VStack spacing={5}>
                                                <FormControl id="name">
                                                    <FormLabel>Your Name</FormLabel>
                                                    <InputGroup borderColor="#E0E1E7">
                                                        <InputLeftElement pointerEvents="none">
                                                            <BsPerson color="gray.800" />
                                                        </InputLeftElement>
                                                        <Input type="text" size="md" placeholder={'Write your name'}/>
                                                    </InputGroup>
                                                </FormControl>
                                                <FormControl id="name">
                                                    <FormLabel>Your Email</FormLabel>
                                                    <InputGroup borderColor="#E0E1E7">
                                                        <InputLeftElement pointerEvents="none">
                                                            <MdOutlineEmail color="gray.800" />
                                                        </InputLeftElement>
                                                        <Input type="text" size="md" placeholder={'Write your email address'}/>
                                                    </InputGroup>
                                                </FormControl>
                                                <FormControl id="name">
                                                    <FormLabel>Message</FormLabel>
                                                    <Textarea
                                                        h={120}
                                                        borderColor="gray.300"
                                                        _hover={{
                                                            borderRadius: 'gray.300',
                                                        }}
                                                        placeholder="Type a message"
                                                    />
                                                </FormControl>
                                                <FormControl id="name" float="right">
                                                    <Button variant="solid" bg="#0D74FF" color="white" _hover={{}}>
                                                        Send Message
                                                    </Button>
                                                </FormControl>
                                            </VStack>
                                        </Box>
                                    </Box>
                                </WrapItem>
                            </Wrap>
                        </Box>
                    </Box>
                </Flex>
            </Container>

        </SidebarWithHeader>
    )

}
export default Contact;