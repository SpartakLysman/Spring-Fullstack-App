import {useAuth} from "../context/AuthContext.jsx";
import {useNavigate} from "react-router-dom";
import {useEffect} from "react";
import {Box, Flex, Heading, Image, Link, Stack, Text} from "@chakra-ui/react";
import CreateCustomerForm from "../shared/CreateCustomerForm.jsx";

const Signup = () => {
    const {customer, setCustomerFromToken } = useAuth();
    const navigate = useNavigate();

    useEffect(() => {
        if (customer) {
            navigate("/dashboard/customers");
        }
    })

    return (
        <Stack minH={'100vh'} direction={{base: 'column', md: 'row'}}>
            <Flex p={8} flex={1} m={200} alignItems={''} justifyContent={'center'}>
                <Stack spacing={4} w={'full'} maxW={'md'}>
                    <Image
                        src={"https://raw.githubusercontent.com/SpartakLysman/Spring-Fullstack-App/main/logo.png"}
                        boxSize={"240px"}
                        alt={"Spartakuss Logo"}
                        borderRadius={"full"}
                        alignSelf={"center"}
                    />
                    <Heading fontSize={'2xl'} mb={0}>Register for an account</Heading>
                    <CreateCustomerForm onSuccess={(token) => {
                        localStorage.setItem("access_token", token)
                        setCustomerFromToken();
                        navigate("/dashboard");
                    }}/>
                    <Link color={"blue.500"} href={"/"}>
                        Have an account? Login now.
                    </Link>
                </Stack>
            </Flex>
            <Flex
                flexDirection={"column"}
                alignItems={"center"}
                justifyContent={"center"}
            >

                <Image
                    alt={'Login Image'}
                    objectFit={'fill'}
                    src={
                        'https://raw.githubusercontent.com/SpartakLysman/Spring-Fullstack-App/main/registration_image.png'
                    }
                    witdth={800}
                    height={1350}

                    // Adjust the size of the image
                />
                <Box
                    position="absolute"
                    top="82.5%"
                    left="82.5%"
                    transform="translate(-50%, -50%)"
                    color="white"
                    fontWeight="bold"
                    fontSize="3xl"
                    display="flex"
                    alignItems="center"
                    justifyContent="center"
                >
                    <Link href={"https://www.linkedin.com/in/spartak-lysman/"}>
                        <Text as='b' fontSize={"4xl"} color={"black"} fontWeight={"bold"}>
                            LinkedIn
                        </Text>
                    </Link>
                    <h1> 📄</h1>
                    <Box width="180px"/> {/* Space between the links */}
                    <Link href={"https://github.com/SpartakLysman"}>
                        <Text as='b' fontSize={"4xl"} color={"black"} fontWeight={"bold"}>
                            GitHu️b
                        </Text>
                    </Link>
                    <h1> 🛠️</h1>
                </Box>
            </Flex>
        </Stack>
    );
}

export default Signup;