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
    Textarea,
    Link,
    Select, Center,
} from '@chakra-ui/react';
import {
    MdPhone,
    MdLocationOn, MdEmail,
} from 'react-icons/md';
import {BsGithub, BsDiscord, BsLinkedin} from 'react-icons/bs';
import {useState} from 'react';
import SidebarWithHeader from "../shared/SideBar.jsx";

const Contact = () => {
    const [formData, setFormData] = useState({
        name: '',
        message: '',
        platform: 'whatsapp'
    });

    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        const {message, platform} = formData;
        let url;

        switch (platform) {
            case 'whatsapp':
                url = `https://wa.me/380735543316?text=${encodeURIComponent(message)}`;
                break;
            case 'telegram':
                url = `https://t.me/Spartak_Lysman?text=${encodeURIComponent(message)}`;
                break;
            default:
                break;
        }
        window.open(url, '_blank');
        setFormData({name: '', message: '', platform: 'whatsapp'});
    };

    return (
        <SidebarWithHeader>
            <Container
                bg="mediumturquoise"
                maxW="full"
                mt={30}
                centerContent
                overflow="hidden"
                w={{base: 'full', md: 'full'}}
                h="1085"
            >
                <Flex>
                    <Box
                        bg="#02054B"
                        color="white"
                        borderRadius="lg"
                        m={{sm: 4, md: 16, lg: 10}}
                        p={{sm: 5, md: 5, lg: 16}}
                    >
                        <Box p={2} h={520}>
                            <Wrap spacing={{base: 5, sm: 3, md: 5, lg: 25}}>
                                <WrapItem>
                                    <Box>
                                        <Heading paddingTop={39} paddingLeft={70} m={1}>Contact</Heading>
                                        <Box py={{base: 5, sm: 5, md: 8, lg: 50}}>
                                            <VStack pl={0} spacing={3} alignItems="flex-start">
                                                <Button
                                                    size="md"
                                                    height="48px"
                                                    width="270px"
                                                    variant="ghost"
                                                    color="#DCE2FF"
                                                    _hover={{border: '2px solid #1C6FEB'}}
                                                    leftIcon={<MdEmail color="#1970F1" size="20px"/>}
                                                >
                                                    spartaklysman@gmail.com
                                                </Button>
                                                <Button
                                                    size="md"
                                                    height="48px"
                                                    width="270px"
                                                    variant="ghost"
                                                    color="#DCE2FF"
                                                    _hover={{border: '2px solid #1C6FEB'}}
                                                    leftIcon={<MdPhone color="#1970F1" size="20px"/>}
                                                >
                                                    +48571518679
                                                </Button>
                                                <Button
                                                    size="md"
                                                    height="48px"
                                                    width="270px"
                                                    variant="ghost"
                                                    color="#DCE2FF"
                                                    _hover={{border: '2px solid #1C6FEB'}}
                                                    leftIcon={<MdLocationOn color="#1970F1" size="20px"/>}
                                                >
                                                    Warsaw, Poland
                                                </Button>
                                            </VStack>
                                        </Box>
                                        <HStack
                                            spacing={5}
                                            px={10}
                                            pt={4}
                                            alignItems="center"
                                        >
                                            <Link href="https://www.linkedin.com/in/spartak-lysman/" isExternal>
                                                <IconButton
                                                    aria-label="linkedin"
                                                    variant=""
                                                    size="lg"
                                                    isRound
                                                    _hover={{bg: '#0D74FF'}}
                                                    icon={<BsLinkedin size="35px"/>}
                                                />
                                            </Link>
                                            <Link href="https://github.com/SpartakLysman" isExternal>
                                                <IconButton
                                                    aria-label="github"
                                                    variant=""
                                                    size="lg"
                                                    isRound
                                                    _hover={{bg: '#0D74FF'}}
                                                    icon={<BsGithub size="35px"/>}
                                                />
                                            </Link>
                                            <IconButton
                                                aria-label="discord"
                                                variant=""
                                                size="lg"
                                                isRound
                                                _hover={{bg: '#0D74FF'}}
                                                icon={<BsDiscord size="35px"/>}
                                            />
                                        </HStack>
                                    </Box>
                                </WrapItem>
                                <WrapItem>
                                    <Box
                                        position="absolute"
                                        top="82.5%"
                                        left="73.5%"
                                        transform="translate(-195%, -365%)"
                                        display="flex"
                                        alignItems="center"
                                        justifyContent="center"
                                        fontSize="2xl"
                                    >
                                        <Text
                                            mt={{sm: 3, md: 3, lg: 5}}
                                            color="gray.100"
                                            marginY="160"
                                            marginX={-63}
                                        >
                                            Form below to contact me!
                                        </Text>
                                    </Box>
                                    <Box bg="white" borderRadius="lg" m={42}>
                                        <Box m={30} h={370} w={550} color="#0B0E3F">
                                            <VStack spacing={5} as="form" onSubmit={handleSubmit}>
                                                <FormControl id="platform">
                                                    <Center>
                                                        <FormLabel paddingTop={2} paddingBottom={1} fontWeight={600}
                                                                   fontSize={20}>Choose Messaging Platform</FormLabel>
                                                    </Center>
                                                    <Select
                                                        name="platform"
                                                        value={formData.platform}
                                                        onChange={handleChange}
                                                        borderColor="#E0E1E7"
                                                    >
                                                        <option value="whatsapp">WhatsApp</option>
                                                        <option value="telegram">Telegram</option>
                                                    </Select>
                                                </FormControl>

                                                <FormControl id="message">
                                                    <Center>
                                                        <FormLabel paddingTop={3} paddingBottom={1} fontWeight={600}
                                                                   fontSize={20}>Type Your Message</FormLabel>
                                                    </Center>
                                                    <Textarea
                                                        h={120}
                                                        borderColor="gray.300"
                                                        _hover={{
                                                            borderRadius: 'gray.300',
                                                        }}
                                                        placeholder="Message"
                                                        name="message"
                                                        value={formData.message}
                                                        onChange={handleChange}
                                                    />
                                                </FormControl>
                                                <FormControl paddingTop={5}>
                                                    <Center>
                                                        <Button
                                                            size="lg"
                                                            type="submit"
                                                            variant="solid"
                                                            bg="#0D74FF"
                                                            color="white"
                                                            _hover={{}}
                                                        >
                                                            Send Message
                                                        </Button>
                                                    </Center>
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
    );
};

export default Contact;